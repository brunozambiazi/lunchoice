
package com.brunozambiazi.framework.model;

import lombok.Getter;
import lombok.Setter;
import org.junit.Assert;
import org.junit.Test;


@SuppressWarnings("serial")
public class BasicEntityTest {

	@Test
	public void longIdEqualsAndHashCode() {
		LongEntity entity1 = new LongEntity();
		Assert.assertNotEquals(entity1, null);

		LongEntity entity2 = new LongEntity();
		Assert.assertNotEquals(entity1.hashCode(), entity2.hashCode());
		Assert.assertNotEquals(entity1, entity2);
		
		entity1.setId(1L);
		Assert.assertNotEquals(entity1.hashCode(), entity2.hashCode());
		Assert.assertNotEquals(entity1, entity2);
		
		entity2.setId(2L);
		Assert.assertNotEquals(entity1.hashCode(), entity2.hashCode());
		Assert.assertNotEquals(entity1, entity2);
		
		entity2.setId(entity1.getId());
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		Assert.assertEquals(entity1, entity2);
		
		entity1.setId(100L);
		entity2.setId(100L);
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		Assert.assertEquals(entity1, entity2);
		
		entity2 = entity1;
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		Assert.assertEquals(entity1, entity2);
	}
	
	@Test
	public void stringIdEquals() {
		StringEntity entity1 = new StringEntity();
		Assert.assertNotEquals(entity1, null);
		
		StringEntity entity2 = new StringEntity();
		Assert.assertNotEquals(entity1.hashCode(), entity2.hashCode());
		Assert.assertNotEquals(entity1, entity2);
		
		entity1.setId("a");
		Assert.assertNotEquals(entity1.hashCode(), entity2.hashCode());
		Assert.assertNotEquals(entity1, entity2);
		
		entity2.setId("b");
		Assert.assertNotEquals(entity1.hashCode(), entity2.hashCode());
		Assert.assertNotEquals(entity1, entity2);
		
		entity2.setId(entity1.getId());
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		Assert.assertEquals(entity1, entity2);
		
		entity1.setId("abc");
		entity2.setId("abc");
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		Assert.assertEquals(entity1, entity2);
		
		entity2 = entity1;
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		Assert.assertEquals(entity1, entity2);
	}
	
	@Test
	public void miscIdEquals() {
		LongEntity longEntity = new LongEntity();
		StringEntity stringEntity = new StringEntity();
		
		Assert.assertNotEquals(longEntity, stringEntity);
		Assert.assertFalse(longEntity.equals(stringEntity));

		Assert.assertNotEquals(stringEntity, longEntity);
		Assert.assertFalse(stringEntity.equals(longEntity));
	}
	
	
	class LongEntity extends BasicEntity<Long> {

		@Getter @Setter
		private Long id;
		
	}
	
	class StringEntity extends BasicEntity<String> {

		@Getter @Setter
		private String id;
		
	}
	
}
