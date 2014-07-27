import play.Project._

name := "iClinic"

version := "1.0"

libraryDependencies ++= Seq (
  jdbc,
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "org.squeryl" %% "squeryl" % "0.9.5-6",
  "org.webjars" %% "webjars-play" % "2.2.2-1",
  "org.webjars" % "bootstrap" % "3.2.0",
  "org.webjars" % "bootstrap-glyphicons" % "bdd2cbfba0",
  "org.webjars" % "angularjs" % "1.2.20"
)

playScalaSettings