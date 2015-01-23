package com.gl.glfancycoverflow.model;

import java.io.Serializable;

/**
 * @author LittleLiByte
 * 
 */
public class FilmInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String filmName;
	private String filmImageLink;
	private String fileDetailUrl;

	public FilmInfo(String filmName, String filmImageLink, String fileDetailUrl) {
		super();
		this.filmName = filmName;
		this.filmImageLink = filmImageLink;
		this.fileDetailUrl = fileDetailUrl;
	}

	public String getFilmName() {
		return filmName;
	}

	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}

	public String getFilmImageLink() {
		return filmImageLink;
	}

	public void setFilmImageLink(String filmImageLink) {
		this.filmImageLink = filmImageLink;
	}

	public String getFileDetailUrl() {
		return fileDetailUrl;
	}

	public void setFileDetailUrl(String fileDetailUrl) {
		this.fileDetailUrl = fileDetailUrl;
	}

	@Override
	public String toString() {
		return "FilmInfo [filmName=" + filmName + ", filmImageLink="
				+ filmImageLink + ", fileDetail=" + fileDetailUrl + "]";
	}

}
