# GraphWalker-Examples

GraphWalker is a Model-Based Testing tool. It parses models [finite-state machines] which are designed
using the yEd, http://www.yworks.com/en/products_yed_about.html, and generates test sequences.
The tool generates offline and more importanly, online test sequences from Finite State Machines and
Extended Finite State Machines.
See also: http://graphwalker.org

This is a set of examples of how to use GraphWalker

## Reporting Issues

https://github.com/KristianKarl/GraphWalker-Examples/issues

## Build
The latest version of GraphWalker is needed, and until it's accessible from a maven repository, it
needs to be locally installed.

*   Download graphwalker-2.5.16-SNAPSHOT.jar from http://graphwalker.org:8080/job/graphwalker/lastStableBuild/org.graphwalker$graphwalker//

*   Install it:

    <mvn install:install-file -Dfile=~/Downloads/graphwalker-2.5.16-SNAPSHOT.jar -DgroupId=org.graphwalker -DartifactId=graphwalker -Dversion=2.5.16-SNAPSHOT -Dpackaging=jar>

## Run the examples

mvn test

## Other documantation

http://graphwalker.org/documentation/

## License

http://graphwalker.org/license/