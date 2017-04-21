package info.vnk.billex.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

@Table(name = "Items")
public class Item extends Model {
	@SerializedName("name")
    @Column(name = "Name")
	public String name;
 
	@Column(name = "Category")
	public String category;
}