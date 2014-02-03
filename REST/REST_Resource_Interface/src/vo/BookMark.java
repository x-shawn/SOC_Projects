package vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookMark implements Cloneable {
	private int id;
	private String title;
	private String color;
	private String comment;

	public BookMark() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Since all attributes of BookMark are either primitive types or String
	 * (i.e. immutable), we could implement @Cloneable interface and override
	 * {@link #clone()} method.
	 */
	@Override
	public BookMark clone() {
		BookMark copy = null;

		try {
			copy = (BookMark) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new RuntimeException(ex);
		}

		return copy;
	}
}
