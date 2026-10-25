package com.example.demo;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.demo.entity.EntityInventoryBalance;
import com.example.demo.entity.EntityLocation;
import com.example.demo.entity.EntityMovementType;
import com.example.demo.entity.EntityProduct;
import com.example.demo.entity.EntityStockMovement;
import com.example.demo.repository.InventoryBalanceRepository;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StockMovementRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static org.assertj.core.api.Assertions.assertThat;

class DemoApplicationTests {

	private ConfigurableApplicationContext context;
	private ProductRepository products;
	private LocationRepository locations;
	private InventoryBalanceRepository balances;
	private StockMovementRepository movements;
	private EntityManager entityManager;
	private PlatformTransactionManager transactionManager;
	private TransactionStatus transaction;
	private Integer productId;

	@BeforeEach
	void setUp() {
		context = new SpringApplicationBuilder(DemoApplication.class)
				.web(WebApplicationType.NONE)
				.run();
		products = context.getBean(ProductRepository.class);
		locations = context.getBean(LocationRepository.class);
		balances = context.getBean(InventoryBalanceRepository.class);
		movements = context.getBean(StockMovementRepository.class);
		entityManager = context.getBean(EntityManager.class);
		transactionManager = context.getBean(PlatformTransactionManager.class);
		transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
	}

	@AfterEach
	void cleanUp() {
		try {
			if (transaction != null && !transaction.isCompleted()) {
				transactionManager.rollback(transaction);
			}
			if (productId != null) {
				assertThat(products.existsById(productId)).isFalse();
			}
		} finally {
			if (context != null) {
				context.close();
			}
		}
	}

	@Test
	void inventoryMappingsRoundTripThroughPostgres() {
		var product = new EntityProduct();
		product.setSku("jpa-test-" + UUID.randomUUID());
		product.setName("Test product");
		products.saveAndFlush(product);
		productId = product.getId();

		var location = new EntityLocation();
		location.setName("Test location");
		locations.saveAndFlush(location);

		var balance = new EntityInventoryBalance();
		balance.setProduct(product);
		balance.setLocation(location);
		balance.setQuantity(new BigDecimal("12.345"));
		balances.saveAndFlush(balance);

		for (var type : EntityMovementType.values()) {
			var movement = new EntityStockMovement();
			movement.setProduct(product);
			movement.setLocation(location);
			movement.setType(type);
			movement.setQuantity(new BigDecimal("1.234"));
			movements.saveAndFlush(movement);
			entityManager.clear();

			var stored = movements.findById(movement.getId()).orElseThrow();
			assertThat(stored.getType()).isEqualTo(type);
			assertThat(stored.getQuantity()).isEqualByComparingTo("1.234");
			assertThat(stored.getCreatedAt()).isNotNull();
			assertThat(stored.getProduct().getSku()).isEqualTo(product.getSku());
			assertThat(stored.getLocation().getName()).isEqualTo(location.getName());
		}

		var storedProduct = products.findById(product.getId()).orElseThrow();
		assertThat(storedProduct.getUnit()).isEqualTo("each");
		assertThat(storedProduct.isActive()).isTrue();
		assertThat(storedProduct.getCreatedAt()).isNotNull();
		assertThat(storedProduct.getUpdatedAt()).isNotNull();

		var storedBalance = balances.findById(balance.getId()).orElseThrow();
		assertThat(storedBalance.getQuantity()).isEqualByComparingTo("12.345");
		assertThat(storedBalance.getUpdatedAt()).isNotNull();
		storedBalance.setQuantity(new BigDecimal("10.111"));
		balances.flush();
		entityManager.clear();
		assertThat(balances.findById(balance.getId()).orElseThrow().getQuantity())
				.isEqualByComparingTo("10.111");
	}
}
