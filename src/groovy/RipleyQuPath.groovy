import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import qupath.lib.objects.PathObjects
import qupath.lib.roi.interfaces.ROI
import qupath.lib.scripting.QP
import org.locationtech.jts.geom.MultiPoint
import org.locationtech.jts.geom.LineString
import org.locationtech.jts.operation.distance.DistanceOp
import org.locationtech.jts.linearref.LengthIndexedLine
import qupath.lib.roi.ROIs
import javafx.scene.chart.LineChart
import javafx.stage.Stage
import java.nio.file.FileSystems

REMOVE_PROJECTED_POINTS = true
// get line and points
def plane = QP.getAnnotationObjects().find( {
    it.getROI().getRoiType()==ROI.RoiType.POINT
}).getROI().getImagePlane()
def points = QP.getAnnotationObjects().find( {
    it.getROI().getRoiType()==ROI.RoiType.POINT
}).getROI().getGeometry() as MultiPoint
def lineroi = QP.getAnnotationObjects().find( {
    it.getROI().getRoiType()==ROI.RoiType.LINE
}).getROI().getGeometry() as LineString
// get some static properties:
int N = points.getNumGeometries()
int N_distances = (N*N)-N
double linelength = lineroi.getLength()
print("Line Length: "+linelength+" pixels")
// project points on line
double[] x = new double[N]
double[] y = new double[N]
for (int i = 0; i < N; i++) {
    def point = points.getGeometryN(i)
    def coords = DistanceOp.nearestPoints(lineroi, point)
    x[i] = coords[0].getX()
    y[i] = coords[0].getY()
}
def roi = ROIs.createPointsROI(x,y,plane)
def annotation = PathObjects.createAnnotationObject(roi, QP.getPathClass('projected points'))
QP.addObject(annotation)
// get position of projected points on line
def lil = new LengthIndexedLine(lineroi)
double[] location_on_line = new double[N]
for (int i = 0; i < N; i++) {
    location_on_line[i] =lil.project(roi.getGeometry().getGeometryN(i).getCoordinate())
}
if (REMOVE_PROJECTED_POINTS){
    QP.removeObject(annotation, false)
}

// calculate distances between points
Distance[] distances = new Distance[N_distances]
int idx = 0
for (int i = 0; i < N; i++) { //from i
    for (int j = i+1; j < N; j++) { // only looking at distance i->j once, thus giving it weight 2
        double d = Math.abs(location_on_line[i] - location_on_line[j])
        distances[idx] = new Distance(d,2.0)
        idx += 1
        distances[idx] = new Distance(linelength-d,2.0) // loop via other side
        idx += 1
    }
}
// sort all distances and the corresponding weights
Arrays.sort(distances)
// get Ripleys K-function
double[] k_func = new double[distances.size()]
k_func[0] = distances[0].weight/N_distances
for (int i = 1; i<distances.size();i++){
    k_func[i] = k_func[i-1]+(distances[i-1].weight/N_distances)
}
// get Ripleys L-function
double[] l_func = new double[distances.size()]
for (int i = 0; i<distances.size();i++){
    l_func[i] = linelength*k_func[i] - 2*distances[i].dist
}
// plot sdistances vs Ripleys L-function
Platform.runLater {
    def xax = new NumberAxis()
    xax.setLabel("Distance (pix)")
    def yax = new NumberAxis()
    yax.setLabel("Ripleys L(r)")
    def chart = new LineChart(xax,yax)
    chart.setCreateSymbols(false)
    chart.setLegendVisible(false)
    def series1 = new XYChart.Series()
    for (int i = 0; i<l_func.size();i++) {
        if (distances[i].dist<linelength/2) {
            series1.getData().add(new XYChart.Data(distances[i].dist, l_func[i]))
        }
    }
    series1.setName("Data")
    chart.getData().addAll(series1)
    def scene  = new Scene(chart, 500, 400)
    def stage = new Stage()
    stage.setTitle("Ripleys Curve")
    stage.setScene(scene)
    stage.show()
}

// store distances and weights
def proj = QP.getProject()
def imagename = QP.getCurrentImageName()
def outpth = FileSystems.getDefault().getPath(proj.getPath().parent as String, imagename+"_Ripley.tsv")
File file = new File(outpth.toString())
def txt = 'Distance\t Ripley L(r)'+System.lineSeparator()
for (int i = 1; i<distances.size();i++){
    txt += distances[i].dist + '\t ' + l_func[i] + System.lineSeparator()
}
file.write(txt)
print(outpth.toString())

// Helper classes
class Distance implements Comparable<Distance>
{
    // To store two values and be able to sort on one of them
    double dist;
    double weight;
    Distance (double dist, double weight)
    {
        this.dist = dist;
        this.weight = weight;
    }
    int compareTo(Distance other) //making it only compare dist values
    {
        if (this.dist > other.dist){
            return 1
        }
        if (this.dist == other.dist){
            return 0
        }
        return -1
    }
    String toString(){
        return this.dist.toString() + ", " + this.weight.toString()
    }
}