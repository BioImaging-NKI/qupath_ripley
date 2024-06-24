# 1D Ripleys analysis in QuPath
One dimensional Ripleys analysis of points on a line. This analysis looks at the interpoint distances and normalizes them for a random distribution. The resulting graph shows deviation from zero for clustered points. Edge correction is done by "connecting" the endpoints of the line. This imposes a symmetry on the the resulting ripley curve, because each set of two points has two paths to connect them. 

 ## Simulations
Simulations have been performed in python. 

Ripley curve when simulating 100 random points on a line of 10 μm long:
![100 random points](imgs/random.png?raw=true "Random")

As can be seen, the lines are around zero for random points.

Ripley curve when simulating 50 random points and 50 clustered points in a cluster at 5 μm with a standard deviation of 0.05 μm on a line of 10 μm long:
![50 random and 50 clustered points](imgs/clustered.png?raw=true "Clustered")

Here there is a clear deviation from zero that shows the clustering. Figures have been made with [this jupyternotebook](src/python/RipleySimulation.ipynb).

## Groovy Script
Groovy script can be found [here](src/groovy/RipleyQuPath.groovy).

It requires a line annotation and a multipoint annotation:
![QuPath screenshot](imgs/QuPath_Start.jpg?raw=true "QuPath")

After running the script a graph of the ripley curve is shown and the raw data is saved as .tsv.
![QuPath screenshot](imgs/QuPath_Graph.PNG?raw=true "QuPath")

The annotations and a link to the image can be found [here](example_data).

## Usage
### Field cancerization in mammary tissue is driven by protection mechanisms that clear mutations
by: Marta Ciwinska, Hendrik A. Messal, Hristina Hristova, Catrin Lutz, Laura Bornes, Theofilius Chalkiadakis, Rolf Harkes, Nathalia S.M. Langedijk, Stefan J. Hutten, Jos Jonkers, Stefan Prekovic, Grand Challenge PRECISION consortium, Benjamin D. Simons, Colinda L.G.J. Scheele, and Jacco van Rheenen

Corresponding authors: Colinda Scheele: (colinda.scheele@kuleuven.be), Jacco van Rheenen: (j.v.rheenen@nki.nl) and Benjamin Simons: (bds10@cam.ac.uk)

## Technical questions
r.harkes@nki.nl or bioimaging@nki.nl

## References
Original paper for 2D point patterns: https://doi.org/10.2307/3212829 
