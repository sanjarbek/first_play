package models

import org.squeryl.{Table, Schema}
import org.squeryl.PrimitiveTypeMode._

object Database extends Schema {
  val clientsTable: Table[Client] = table[Client]("clients")

  on(clientsTable) { c => declare{
    c.id is(autoIncremented("clients_id_seq"))
  }}
}