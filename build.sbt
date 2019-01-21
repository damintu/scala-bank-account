name := "projects"
 
version := "1.0" 
      
lazy val `projects` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "io.rest-assured" % "scala-support" % "3.3.0"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      