package com.share.web.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Attachment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "attachment", catalog = "share_db")
public class Attachment implements java.io.Serializable {

	// Fields

	private Integer id;
	private String path;
	private String caption;
	private Timestamp createTime;
	private Integer size;

	// Constructors

	/** default constructor */
	public Attachment() {
	}

	/** minimal constructor */
	public Attachment(String path) {
		this.path = path;
	}

	/** full constructor */
	public Attachment(String path, String caption,
			Timestamp createTime, Integer size) {
		this.path = path;
		this.caption = caption;
		this.createTime = createTime;
		this.size = size;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	@Column(name = "path", nullable = false, length = 64)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "caption", length = 100)
	public String getCaption() {
		return this.caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@Column(name = "createTime", length = 0)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "size")
	public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}


}