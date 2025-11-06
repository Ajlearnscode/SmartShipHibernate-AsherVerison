package com.java.hibernate;

import java.util.ArrayList;

public class Package {

		private int pkgId;
		private ArrayList pkgItems;
		private Date deliveryDate;
		private float pkgWieght;
		private Freight pkgFreight;
		private String courier;
		
		class Freight{
			private float length;
			private float width;
			private float height;
		}
		
		//DEFAULT CONSTRUCTOR
		public Package() {
			this.pkgId = 0;
			this.pkgItems = new ArrayList<>();
			this.deliveryDate = new Date();
			this.pkgWieght = 0.0f;
			this.pkgFreight = new Freight();
			this.courier = "";
		}
		
		
}
