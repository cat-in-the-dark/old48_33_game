name := "old48_33_game"

version := "1.0"

scalaVersion := "2.11.7"

val gdxVersion = "1.7.1"

fork in Compile := true

unmanagedResourceDirectories in Compile += file("assets")

libraryDependencies ++= Seq(
  "com.badlogicgames.gdx" % "gdx" % gdxVersion,
  "com.badlogicgames.gdx" % "gdx-backend-lwjgl" % gdxVersion,
  "com.badlogicgames.gdx" % "gdx-platform" % gdxVersion classifier "natives-desktop",
  "com.badlogicgames.gdx" % "gdx-freetype" % gdxVersion,
  "com.badlogicgames.gdx" % "gdx-freetype-platform" % gdxVersion classifier "natives-desktop",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.1"
)