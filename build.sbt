name := "scala-bank-account"
maintainer := "dsarramalho@wemanity.com"
version := "1.0" 
      
lazy val `projects` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  ehcache ,
  ws ,
  specs2 % Test ,
  guice,
  "mysql" % "mysql-connector-java" % "8.0.15",
  "org.scalactic" %% "scalactic" % "3.0.5",
  "io.rest-assured" % "scala-support" % "3.3.0",
  "com.typesafe.play" %% "play-slick" % "3.0.3",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.3",
  "org.scalactic" %% "scalactic" % "3.0.5",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % "test"
  )
parallelExecution in Test := false
