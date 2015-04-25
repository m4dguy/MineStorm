import java.util.Arrays;

/**
 * Object for storing vector graphic information.
 * Contains methods for construction, alignment and bounding circle calculation.
 * Carries node and edge information about the object.
 * The nodes are stored in an unmodified representation.
 * For applying transformations, add this node tho a MeshModifier.
 *
 * Created by m4dguy on 24.02.2015.
 */
public class Mesh {

    float bound;
    float centerX, centerY;
    protected float[][] nodes;
    protected int[][] edges;

    /**
     * Mesh construction with custom arrays.
     * Make sure the arrays are actually valid!
     * @param otherNodes absolute node positions.
     * @param otherEdges edge list starting at 0.
     */
    public Mesh(float[][] otherNodes, int[][] otherEdges) {
        nodes = otherNodes;
        edges = otherEdges;

        align();
        calcBound();
    }

    /**
     * Returns the size of the node array.
     * @return number of nodes.
     */
    public int getNodeCount(){
        return nodes.length;
    }

    /**
     * Returns the size of the edge array.
     * @return number of edges.
     */
    public int getEdgeCount(){
        return edges.length;
    }

    /**
     * Manually set the center of the Mesh.
     * This center is treated as a reference point for all transformations (displacement, scaling and rotation).
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public void setCenter(float x, float y){
        centerX = x;
        centerY = y;
    }

    /**
     * Calculates and stores the maximum radius of this Mesh for the purpose of constructing a bounding sphere.
     */
    public void calcBound(){
        bound = 0f;
        float dx, dy;
        for(int i=0; i<getNodeCount(); ++i) {
            dx = centerX - nodes[i][0];
            dy = centerY - nodes[i][1];
            bound = (float)Math.max(Math.sqrt(dx*dx + dy*dy), bound);
        }
    }

    /**
     * Calculates and stores the center coordinate of this Mesh (x position).
     */
    protected void calcCenterX(){
        for(int i=0; i<getNodeCount(); ++i){
            centerX += nodes[i][0];
        }
        centerX /= getNodeCount();
    }

    /**
     * Calculates and stores the center coordinate of this Mesh (y position).
     */
    protected void calcCenterY(){
        for(int i=0; i<getNodeCount(); ++i){
            centerY += nodes[i][1];
        }
        centerY /= getNodeCount();
    }

    /**
     * Aligns the Mesh by moving it to the origin.
     * Useful for preparing subsequent transformations.
     */
    protected void align(){
        calcCenterX();
        calcCenterY();
        //centerX = .5f;
        //centerY = .5f;
        for(int i=0; i<getNodeCount(); ++i){
            nodes[i][0] -= centerX;
            nodes[i][1] -= centerY;
        }
    }

}
