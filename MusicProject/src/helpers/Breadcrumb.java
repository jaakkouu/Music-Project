package helpers;

import java.util.ArrayList;
import java.util.List;

public class Breadcrumb {

	private List<String> breadcrumb = new ArrayList<>();
	
	public void setItem(String type, String link, String string) {
		String item = "";
		if(type.equals("link")) {
			item = "<a href='"+link+"'>"+string+"</a>";
		} else {
			item = "<span>"+string+"</span>";
		}
		breadcrumb.add(item);
	}
	
	public List getBreadcrumb() {
		return this.breadcrumb;
	}

}
