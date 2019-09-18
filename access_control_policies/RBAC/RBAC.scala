import scala.collection.mutable._;
import scala.io.Source;

object RBACPolicy{
	type U = String;
	type R = String; // roles
	type P = String; // permissions
	type S = Object; // sessions

	//stable 
	var users = Set[U]("alice", "bob","carl")
	var roles = Set[R]("teacher", "reasearcher", "student")
	var perms = Set[P]("library","bookroom", "frittenDiscount")
	var users_roles = Map[U,Set[R]]("alice"->Set("teacher","researcher"),"bob"->Set("teacher"), "carl"->Set("student"))
	var role_perms = Map[R,Set[P]]("teacher"->Set("library"), "researcher"->Set("bookroom"), "student"->Set("frittenDiscount"))
	
	// dynamic
	var sessions = Set[S]()
	var sessions_roles = Map[S,Set[R]]()
	var sessions_user = Map[S,U]()

	def checkAccess(s:S, p:P){
		require(sessions_roles(s).exists(r =>role_perms(r).contains(p)))
		println("checked access to "+p+" for user "+sessions_user(s))
	
	}
	
	def addSession(u:U, rs:Set[R]):S={
		require(rs.subsetOf(users_roles(u)))
		val s = new S()
		sessions+=s
		sessions_user +=(s->u)
		sessions_roles+=(s->rs)
		
		println("Created new session by "+u+" with roles "+rs)
		
		return s
	}
	
	def addRoleToSession(s:S, r:R){
		require(users_roles(sessions_user(s)).contains(r))
		
		println("added role "+r+" to one of "+sessions_user(s)+"'s sessions")
	}
	
	def removeRoleTosession(s:S,r:R){
		require(sessions_roles(s).contains(r))
		
		println("removed role "+r+" to one of "+sessions_user(s)+" sessions")
	
	}
}

object Main {
	def main(args: Array[String]) {
		val a="alice"; val b="bob"; val c ="carl"
		val S1 = RBACPolicy.addSession(a,Set("teacher"))
		RBACPolicy.checkAccess(S1,"library")
		val S2 = RBACPolicy.addSession(c,Set("student"))
		RBACPolicy.checkAccess(S2,"frittenDiscount")
		RBACPolicy.addRoleToSession(S1,"researcher")
		// RBACPolicy.checkAccess(S1,"frittenDiscount") // fails because teachers do not have permissions for frittenDiscount
	}
}