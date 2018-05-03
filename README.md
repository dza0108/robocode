IntelliJ:

Install IntelliJ, and Robocode

Set the IntelliJ External Libraries to include robocode.jar (robocode/lib/robocode.jar)
ie:
	create a new project with a package
	Project -> right click -> "Open Module Settings" -> Project settings -> Libraries -> add robocode.jar
																		 -> Modules -> Paths ->
																			User module compile output path
																			"<root_robocode"/robots

	this will add the robocode library to be used and place the output class file under robots directory
