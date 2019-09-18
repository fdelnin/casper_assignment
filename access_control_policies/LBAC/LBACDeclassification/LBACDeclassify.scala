import scala.collection.mutable._;
import scala.io.Source;

object LBACPolicy{
	type U = String;
	type O = String;
	type L = String; // labels, should be a lattice
	type S = Object; // sessions

// stable part of the protection state
	var users = Set[U]("alice","bob");
	var ulabel = Map[U,L](("alice"->"AA"), ("bob"->"A"));
	
// dynamic part

	var objects = Set[U]();
	var sessions = Set[S]();
	var slabel = Map[S,L]();
	var olabel = Map[O,L]();
	
// no read up
	def read(s:S, o:O){
		require(slabel(s) >= olabel(o))
		println("Reading file "+o)
	}

// no write down
	def write(s:S, o:O){
		require(slabel(s)<= olabel(o))
			println("Writing file "+o)
	}
	
// managing sessions and objects

	def createSession(u:U, l:L):S={
		require(ulabel(u) >= l)
		val s = new S()
		sessions += s
		slabel += (s -> l)
		
		println("created sesssion with label "+l+" user "+u)
		return s
	}
	
	def createObject(s:S, o:O, l:L){
		require(!objects.contains(o) & slabel(s)<=l)
		objects+=o
		olabel +=(o->l)
		
		println("created object "+o+" with label "+l)
	}

	def declassify(S:S, o:O, l:L){
		require(slabel(S) >= olabel(o), olabel(o) >= l)
		olabel-=o
		olabel += (o -> l)
		println("declassification of file "+o+ " to label "+l)
	}
}


object Main {
	def main(args: Array[String]) {
		val a = "alice"
		val b = "bob"
		val S1 = LBACPolicy.createSession(a,"AA")
		LBACPolicy.createObject(S1,"o1","AA");
		val S2 = LBACPolicy.createSession(b,"A")
		
		LBACPolicy.write(S2,"o1")
		// LBACPolicy.read(S2,"o1") // fails to read up
		LBACPolicy.declassify(S1,"o1","A")
		LBACPolicy.read(S2,"o1")
	}
}