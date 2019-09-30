package farrant.christopher.api.exceptions;

public class NoSuchEntityException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private int id;
	
	private Class<?> type;
	
	public NoSuchEntityException() {}
	
	public NoSuchEntityException(int id, Class<?> type) {
		super("No such entity of type " + type.getName() + " with id " + id);
		this.setId(id);
		this.setType(type);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}
}
