import scala.collection.mutable._;
import scala.io.Source;

object Policy{
	type U = String;
	type R = String; // roles
	type N = Int; // num

//stable part
	var res= Set[R]("r1","r2","r3")
	var users = Set[U]("alice","bob")

//dynamic
	var u_res2 = Map[(R,U),N](("r2","bob")->3,("r1","bob")->2)

	def use(r:R,u:U){
		
		require(u_res2.contains((r,u)))

		u_res2(r,u)-=1
		
		if (u_res2(r,u)==0)
			u_res2-=((r,u))

		println(u+" uses "+r)
	}

	def pay(r:R,u:U){ // require money but we assume payment is always verified
		require(res.contains(r) & users.contains(u))
		
		if(u_res2.contains(r,u))
			u_res2(r,u)+=10
		else u_res2+=((r,u)->10)

		println(u+" pays for "+r)
	}

	def transfer(u:U, n:N, r:R, u2:U) {
		require(users.contains(u2) && u_res2(r,u)>=n )
		
		if(u_res2.contains(r,u2))
			u_res2(r,u2)+=n
		else u_res2+=(r,u2)->n

		u_res2(r,u)-=n
		
		if(u_res2(r,u)==0) 
			u_res2-=((r,u))
		println(u + " transferred " + n + " "+ r + " credits to " + u2)
	}

}
object Main {
	def main(args: Array[String]) {
		val a="alice"; val b="bob"; val c ="carl"
		Policy.use("r2",b)
		Policy.use("r2",b)
		Policy.use("r2",b)
		//Policy.use(b,"r2") //fails because he has no more credits
		Policy.pay("r2",a)
		Policy.use("r2",a)
		Policy.transfer(a,2,"r2",b)
		Policy.pay("r2",a)
		println(Policy.u_res2)

	}
}