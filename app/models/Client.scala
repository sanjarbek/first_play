package models

import java.util.Date

import org.squeryl.{Query, KeyedEntity, Table}
import org.squeryl.PrimitiveTypeMode._
import collection.Iterable

case class Client(
                  id: Long,
                  username: String,
                  firstname: String,
                  surname: String,
                  birthday: Date
                   ) extends KeyedEntity[Long]

object Client {
  import Database.{clientsTable}

  def allQ: Query[Client] = from(clientsTable) {
    client => select(client) orderBy(client.id)
  }

  def findAll: Iterable[Client] = inTransaction{
    this.allQ.toList
  }

  def insert(client: Client): Client = inTransaction{
    clientsTable.insert(client)
  }

  def update(client: Client) = inTransaction{
    clientsTable.update(client)
  }

  def findById(id: Long) = inTransaction{
    from(clientsTable) {
      client => where(client.id===id) select(client)
    }.headOption
  }

  def delete(client: Client) = inTransaction{
    clientsTable.deleteWhere(_.id===client.id)
  }
}

