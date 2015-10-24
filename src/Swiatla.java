
public class Swiatla extends Cordinate{
	Boolean b;
	public Swiatla(double lat, double lon) {
		super(lat, lon);
		this.b=true;
		// TODO Auto-generated constructor stub
	}
	public void change(){
		this.b=!b;
	}
}
