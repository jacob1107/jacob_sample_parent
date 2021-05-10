package org.jacob.spring.es2.bean;

import java.io.Serializable;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

//@Document(indexName = "myindex")
public class Book implements Serializable {

	private static final long serialVersionUID = -7383152854961716387L;
	//@Field(index = false, type = FieldType.Text)
	private String id;
	//@Field(analyzer = "ik_max_word", type = FieldType.Text, store = true, searchAnalyzer = "ik_max_word")
	private String name;
	//@Field(analyzer = "ik_max_word", type = FieldType.Text, store = true, searchAnalyzer = "ik_max_word")
	private String author;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author + "]";
	}

}
