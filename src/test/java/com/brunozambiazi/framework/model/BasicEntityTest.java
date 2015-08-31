
package com.brunozambiazi.framework.model;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;


@SuppressWarnings("serial")
public class BasicEntityTest {

	@Test
	public void longIdEqualsAndHashCode() {
		LongEntity entity1 = new LongEntity();

		LongEntity entity2 = new LongEntity();
		assertThat(entity1.hashCode()).isNotEqualTo(entity2.hashCode());
		assertThat(entity1).isNotEqualTo(entity2);
		
		entity1.setId(1L);
		assertThat(entity1.hashCode()).isNotEqualTo(entity2.hashCode());
		assertThat(entity1).isNotEqualTo(entity2);
		
		entity2.setId(2L);
		assertThat(entity1.hashCode()).isNotEqualTo(entity2.hashCode());
		assertThat(entity1).isNotEqualTo(entity2);
		
		entity2.setId(entity1.getId());
		assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
		assertThat(entity1).isEqualTo(entity2);
		
		entity1.setId(100L);
		entity2.setId(100L);
		assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
		assertThat(entity1).isEqualTo(entity2);
		
		entity2 = entity1;
		assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
		assertThat(entity1).isEqualTo(entity2);
	}
	
	@Test
	public void stringIdEquals() {
		StringEntity entity1 = new StringEntity();
		StringEntity entity2 = new StringEntity();
		assertThat(entity1.hashCode()).isNotEqualTo(entity2.hashCode());
		assertThat(entity1).isNotEqualTo(entity2);
		
		entity1.setId("a");
		assertThat(entity1.hashCode()).isNotEqualTo(entity2.hashCode());
		assertThat(entity1).isNotEqualTo(entity2);
		
		entity2.setId("b");
		assertThat(entity1.hashCode()).isNotEqualTo(entity2.hashCode());
		assertThat(entity1).isNotEqualTo(entity2);
		
		entity2.setId(entity1.getId());
		assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
		assertThat(entity1).isEqualTo(entity2);
		
		entity1.setId("abc");
		entity2.setId("abc");
		assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
		assertThat(entity1).isEqualTo(entity2);
		
		entity2 = entity1;
		assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
		assertThat(entity1).isEqualTo(entity2);
	}
	
	@Test
	public void miscIdEquals() {
		LongEntity longEntity = new LongEntity();
		StringEntity stringEntity = new StringEntity();
		
		assertThat(longEntity).isNotSameAs(stringEntity);
		assertThat(longEntity.equals(stringEntity)).isFalse();

		assertThat(stringEntity).isNotSameAs(longEntity);
		assertThat(stringEntity.equals(longEntity)).isFalse();
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
