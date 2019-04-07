package at.alex.javakurs3.cinema.model;

public enum District {
	
	Innere_Stadt((byte)1),
	Leopoldstadt ((byte)2),
	Josefstadt((byte)8);
	
	private byte districtNo;
	
	District(byte districtNo){
		this.districtNo = districtNo;
	}

	
	public String getName(){
		return this.toString();
	}
	

	public byte getNo(){
		return this.districtNo;
	}
}
