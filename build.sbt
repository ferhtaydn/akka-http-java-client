name := "akka-http-java-client"

organization := "com.ferhtaydn"

version := "0.1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-core" % "2.4.7",
  "com.typesafe.akka" %% "akka-http-testkit" % "2.4.7",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.7",
  "com.typesafe.akka" %% "akka-http-jackson-experimental" % "2.4.7",
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-csv" % "2.7.5",
  "junit" % "junit" % "4.12"  % "test",
  "com.novocode" % "junit-interface" % "0.11"  % "test"
)

assemblyJarName in assembly := "GoEuroTest.jar"
mainClass in assembly := Some("com.ferhtaydn.akkahttpjavaclient.GoEuroTest")

initialCommands := "import com.ferhtaydn.akkahttpjavaclient._"