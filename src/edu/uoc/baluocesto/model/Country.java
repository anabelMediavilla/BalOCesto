package edu.uoc.baluocesto.model;

import java.io.Serializable;

public enum Country implements Serializable{
	AR("Argentina"){
		@Override
		public boolean isEuropean() { return false;}
	},
	BR("Brazil"){
		@Override
		public boolean isEuropean() { return false;}
	},
	DE("Germany"){
		@Override
		public boolean isEuropean() { return true;}
	},
	DK("Denmark"){
		@Override
		public boolean isEuropean() { return true;}
	},
	ES("Spain"){
		@Override
		public boolean isEuropean() { return true;}
	},
	FI("Finland"){
		@Override
		public boolean isEuropean() { return true;}
	},
	FR("France"){
		@Override
		public boolean isEuropean() { return true;}
	},
	GB("United Kingdom"){
		@Override
		public boolean isEuropean() { return true;}
	},
	GR("Greece"){
		@Override
		public boolean isEuropean() { return true;}
	},
	HR("Croatia"){
		@Override
		public boolean isEuropean() { return true;}
	},
	IL("Israel"){
		@Override
		public boolean isEuropean() { return true;}
	},
	IT("Italy"){
		@Override
		public boolean isEuropean() { return true;}
	},
	JP("Japan"){
		@Override
		public boolean isEuropean() { return false;}
	},
	LTU("Lithuania"){
		@Override
		public boolean isEuropean() { return true;}
	},
	MX("Mexico"){
		@Override
		public boolean isEuropean() { return false;}
	},
	NL("Holanda"){
		@Override
		public boolean isEuropean() { return true;}
	},
	TR("Turkey"){
		@Override
		public boolean isEuropean() { return true;}
	},
	US("United States"){
		@Override
		public boolean isEuropean() { return false;}
	},
	YUG("Yugoslavia"){
		@Override
		public boolean isEuropean() { return true;}
	};
	
	
	private String description;
	
	private Country(String description) {
		this.description = description;
	}
	
	public abstract boolean isEuropean();
	
	@Override
	public String toString() {
		return description;
	}
}
