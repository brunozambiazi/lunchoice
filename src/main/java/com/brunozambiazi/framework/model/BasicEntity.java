
package com.brunozambiazi.framework.model;

import java.io.Serializable;


public abstract class BasicEntity<ID extends Serializable> implements BaseEntity<ID> {

	private static final long serialVersionUID = -4087042576004273159L;
	

	@Override
	public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof BaseEntity<?>)) {
            return false;
        }

        BaseEntity<?> other = (BaseEntity<?>)obj;
        return (getId() == null ? super.equals(obj) : getId().equals(other.getId()));
	}

	@Override
	public final int hashCode() {
		return (getId() == null ? super.hashCode() : getId().hashCode());
    }
	
	@Override
	public String toString() {
		return String.format("%s[id=%s, %s]", getClass().getName(), getId(), hashCode());
	}
	
}
