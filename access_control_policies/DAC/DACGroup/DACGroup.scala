import scala.collection.mutable._;
import scala.io.Source;

class DACPolicy{
	type U = String; //Users
	type O = String; //objects
	type A = String; //Actions
	type G = String; //Groups
	type R = (G,O,A);
	
	var users = Set[U]();
	var objects = Set[O]();
	var groups = Set[G]();
	var rights = Set[R](); //Access control matrix
	var groupOf = Map[U,G](); //each user can be in only one group
	var ownerOf = Map[O,U]();
	
	def init() { // an initial config
		users = Set("Alice","Bob");
		objects = Set("obj");
		rights = Set(("GroupOne","obj", "read"));
		groups = Set("GroupOne","G2");
		groupOf =  Map("Alice"->"GroupOne");
		ownerOf = Map("obj"->"Alice");
		
	}
	
//Access cheks
	
	def read(u:U, o:O){
		var g=groupOf(u);
		require(users.contains(u) & objects.contains(o) &  rights.contains((g,o,"read")));
		
		println("read on object "+ o + " by user " + u + " of group " + g);
	}
	
	def write(u:U, o:O){
		var g=groupOf(u);
		require(users.contains(u) & objects.contains(o) & rights.contains((g,o,"write")));
		
		println("write on object "+ o + " by user " + u + " of group " + g);
	}
	
	
//Actions that impact the protection state
	def addRight(u:U, r:R){
		r match { case (g,o,a) => require(groups.contains(g) & objects.contains(o) & ownerOf(o)==u & !rights.contains(g,o,a));
								  rights += r;
								  println("added right '" +a+"' on object "+ o + " by user " + u + " on group " + g);
								  }	
	}
	
	def deleteRight(u:U, r:R){
		r match { case (g,o,a) => require(groups.contains(g) & objects.contains(o) & ownerOf(o)==u & rights.contains(g,o,a));
		                          rights -= r;
								  println("deleted right '" +a+"' on object "+ o + " by user " + u + " on group " + g);
								  }
	}

	
	
	def addObject(s:U, o:O){
		require(!objects.contains(o));
		objects += o;
		ownerOf += (o -> s);
		println("Added object "+ o +" by user "+s);
	}
	
	def deleteObject(u:U, o:O){
		require(objects.contains(o) & ownerOf(o)== u)
		objects-=o;
		ownerOf -=o;
		rights.retain({case (u,o1,a) => o1!=o})
		
		println("deleted object "+ o +" by user "+u);
	}
	
//Admin functions	
	def addUser(s:U,u:U){
		require(!users.contains(u)); //possibly require s to be admin
		users += u;
		println("Added user "+u);
	}
	
	def addGroup(u:U, g:G){ // possibly only admins can add groups
		require(users.contains(u) & !groups.contains(g));
		groups += g;
		println("Created group "+g+" by "+u);
	}
	

	def addToGroup(u:U, g:G, u2:U){ // possibly only admins can add to groups
		require(users.contains(u) & groups.contains(g) & users.contains(u2) );
		groupOf(u2)=g; // will overwrite old group if any 
		println("Added "+u2+" to group "+g+" by "+u);
	}
}
	


object Main {
	def main(args: Array[String]) {
		val dac1 = new DACPolicy();
		//dac1.init(); //an initial config
		
		var a="Alice";
		var b = "Bob";
		dac1.addUser(a,a);
		dac1.addUser(a,b);
		dac1.addGroup(a,"GroupOne");
		dac1.addGroup(a,"G2");
		dac1.addObject(a,"obj");
		dac1.addToGroup(a,"GroupOne",a);
		
		dac1.addRight(a,("GroupOne","obj","read"))
		dac1.read(a,"obj");
		dac1.addObject(b,"file1");
		dac1.addRight(a,("G2","obj","read"));
		//dac1.read("Bob","obj");
		dac1.addGroup(a, "SecretGroup");
		dac1.addToGroup(a,"SecretGroup",b);
		dac1.addRight(a,("SecretGroup","obj","read"));
		dac1.read(b,"obj");
		
		println(dac1.groupOf);

	}
}