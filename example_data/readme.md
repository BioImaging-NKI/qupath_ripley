# Example Data
The image of the Nile can be downloaded [here](http://imagej.net/images/NileBend.jpg). (From the FIJI sample data.)

The file `NileBend_clustered.geojson` was used to create the ripleycurve on the main page.

The file `NileBend_not_clustered.geojson` contain the same line, but different points. 
The resulting graph in QuPath looks like this:
![QuPath screenshot](../imgs/QuPath_Graph_nc.PNG?raw=true "QuPath")
I made the annotations by clicking and found it interesting to see that they still cluster on larger lengthscales. 
This is probably due to the wider dark areas of the river. 
It also shows that I avoided clustering of points on the short lengthscales to an extent that it is anti-clustering. 
This is shown by the ripley curve that goes below zero for short length scales and above zero for large lengthscales.
