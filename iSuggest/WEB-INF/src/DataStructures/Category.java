package DataStructures;

import Utils.TextUtils;

public class Category {
	private String categoryId = null;
	private String category = null;
	
	public String getCategoryId() {
		return categoryId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = TextUtils.zeroToNull(categoryId);
	}
	public void setCategory(String category) {
		this.category = TextUtils.zeroToNull(category);
	}
}