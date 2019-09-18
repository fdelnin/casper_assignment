import scala.collection.mutable._;
import scala.io.Source;

object DACPolicy{
	type U = String; //Users
	type O = String; //objects
	type A = String; //Actions
	type R = (U,O,A);
	var users = Set[U]();
	var objects = Set[O]();
	var rights = Set[R](); //Access control matrix
	var ownerOf = Map[O,U]();
	var restrictedWrite = Set[U]();
	
//Access cheks
	def read(u:U, o:O){
		require(objects.contains(o) & users.contains(u) )
		println(u+" is reading file "+o)
	}
	
	def write(u:U, o:O){
		require(objects.contains(o) & users.contains(u))
		println(u+" is writing in file "+o)
	}
	
//Actions that impact the protection state
	def addRight(s:U, r:R){
		require(users.contains(s))
		r match {case (u,o,a) =>
				require(users.contains(u) & objects.contains(o) & ownerOf(o) == s & !rights.contains((u,o,a)) ); // cannot add an already existing right
					a match { case "write" => 
									require(!restrictedWrite.contains(u))
									rights += r;
									println("added right "  + a + " to user "+u+" by "+s);
							  case _ =>
									rights += r;
									println("added right "  + a + " to user "+u+" by "+s);
					}
				
		}
	}
	
	def delereRight(s:U, r:R){
		require(users.contains(s))
		r match {
			case (u,o,a) =>
				require(ownerOf(o) == s & rights.contains(r)); 
				rights -=r;
				println("deleted right "  + a + " to user "+u+" by "+s);
		}
	}
	
		
	def addObject(s:U, o:O){
		require(!objects.contains(o) & users.contains(s));
		objects += o;
		ownerOf += (o -> s);
		println("added object "+ o +" by user "+s);
	}

	def delObject(s:U,o:O){
		require(objects.contains(o) & ownerOf(o) == s); // restricted user can delete file if it's their own
		objects -= o;
		ownerOf -= o;
		rights.retain( {case(u,o2,a) => o2 !=o} );
		println("deleted object "+ o +" by user "+s);
	}

	
	//admin functions:
	def addUser(s:U,u:U){
		require(!users.contains(u)); //possibly require s to be admin
		users += u;
		println("added user "+u);
	}
	
	def addRUser(s:U,u:U){
		require(!users.contains(u)); //possibly require s to be admin
		users += u;
		restrictedWrite+=u;
		println("added restricted user "+u);
	}
	
}

object Main {
	def main(args: Array[String]) {
		var a = "Alice"
		var b = "Bob"
		var f1 = "file1"
		var r = "read"
		DACPolicy.addUser(a,a)
		DACPolicy.addRUser(a,b)
		DACPolicy.addObject(a,f1)
		DACPolicy.read(b,f1)
		// DACPolicy.addRight(a,(b,f1,"write")) // fails to add right 'write' to restricted user
	}
}