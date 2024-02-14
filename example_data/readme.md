# Example Data
The image of the Nile can be downloaded [here](http://imagej.net/images/NileBend.jpg). (From the FIJI sample data.)

The files `NileBend_clustered.geojson` and `NileBend_not_clustered.geojson` contain the same line, but different points. I made the annotations by clicking and found it interesting to see that in the "not clustered" points they still cluster on larger lengthscales. This is probably due to the wider dark areas of the river. It also shows that I avoided clustering of points on the short lengthscales to an extent that it is anti-clustering. According to the ripley curve that goes below zero for short length scales and above zero for large lengthscales.

