package edu.skku.pms;

import java.io.Serializable;


public class Product implements Serializable{
		private int pcode;
		private String title;
		private int price;
		private String color;
		

		public Product() {
		}

		public Product(int pcode, String title, int price, String color) {
			super();
			this.pcode = pcode;
			this.title = title;
			this.price = price;
			this.color = color;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(pcode);
			builder.append("   ");
			builder.append(title);
			builder.append("   ");
			builder.append(price);
			builder.append("   ");
			builder.append(color);
			return builder.toString();
		}

		public int getPcode() {
			return pcode;
		}

		public void setPcode(int pcode) {
			this.pcode = pcode;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

	
}
