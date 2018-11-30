package ufc.cmu.promocity.backend.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Classe abstrata para servir de base para as classes de modelo
 * @author armandosoaressousa
 *
 * @param <Long>
 */
@MappedSuperclass
public abstract class AbstractModel<Long extends Serializable> implements Serializable {

    private static final long serialVersionUID = -6323358535657100144L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    
    @Column(name="active")
    private boolean active;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractModel<?> other = (AbstractModel<?>) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}