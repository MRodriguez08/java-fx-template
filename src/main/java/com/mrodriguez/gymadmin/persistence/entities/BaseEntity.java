package com.mrodriguez.gymadmin.persistence.entities;

import java.io.File;

public abstract class BaseEntity {
	
	public static File editImage = null;
	private final String editImagePath = "images/edit.jpg";
	public static File deleteImage = null;
	private final String deleteImagePath = "images/delete.png";
		
	public File getEditImage() {
		if (editImage == null)
			editImage = new File(editImagePath);
		return editImage;
	}
	public File getDeleteImage() {
		if (deleteImage == null)
			deleteImage = new File(deleteImagePath);
		return deleteImage;
	}
}
