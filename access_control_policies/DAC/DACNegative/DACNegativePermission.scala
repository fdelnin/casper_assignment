import scala.collection.mutable._;
import scala.io.Source;

object DACPolicy{
	type U = String; //Users
	type O = String; //objects
	type A = String; //Actions
	type NR = (U,O,A);
	var users = Set[U]();
	var objects = Set[O]();
	var neg_rights = Set[NR](); //Access control matrix
	var ownerOf = Map[O,U]();

//Access cheks
	def read(u:U, o:O){
		require(objects.contains(o) & users.contains(u) & !neg_rights.contains((u,o,"read")))
		println(u+" is reading file "+o)
	}
	
	def write(u:U, o:O){
		require(objects.contains(o) & users.contains(u) & !neg_rights.contains((u,o,"write")))
		println(u+" is writing in file "+o)
	}
	
//Actions that impact the protection state
	def addRight(s:U, r:NR){
		require(users.contains(s))
		r match {case (u,o,a) =>
				require(users.contains(u) & objects.contains(o) & ownerOf(o) == s & !neg_rights.contains((u,o,a))); // cannot add an already existing right
				neg_rights += r;
				println("added negative right "  + a + " of user "+u+" by "+s);
		}
	}
	
	def delereRight(s:U, r:NR){
		require(users.contains(s))
		r match {
			case (u,o,a) =>
				require(ownerOf(o) == s & neg_rights.contains(r)); 
				neg_rights -=r;
				println("deleted right "  + a + " of user "+u+" by "+s);
		}
	}
	
	def addObject(s:U, o:O){
		require(!objects.contains(o) & users.contains(s));
		objects += o;
		ownerOf += (o -> s);
		println("added object "+ o +" by user "+s);
	}

	def delObject(s:U,o:O){
		require(objects.contains(o) & ownerOf(o) == s);
		objects -= o;
		ownerOf -= o;
		neg_rights.retain( {case(u,o2,a) => o2 !=o} );
		println("deleted object "+ o +" by user "+s);
	}

//admin functions:
	def addUser(s:U,u:U){
		require(!users.contains(u)); //possibly require s to be admin
		users += u;
		println("added user "+u);
	}
}

object Main {
	def main(args: Array[String]) {
		var a = "Alice"
		var b = "Bob"
		var f1 = "file1"
		var r = "read"
		DACPolicy.addUser(a,a)
		DACPolicy.addUser(a,b)
		DACPolicy.addObject(a,f1)
		DACPolicy.write(b,f1)
		DACPolicy.addRight(a,(b,f1,"write"))
		//bDACPolicy.write(b,f1) // fails
	}
}