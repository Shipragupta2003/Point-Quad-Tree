
//import java.util.LinkedList;
//import java.util.Queue;
import java.util.ArrayList;
import java.util.List;
//import java.util.Stack;
    
        
public class PointQuadtree {
    enum Quad {
        NW,
        NE,
        SW,
        SE
    }

    public PointQuadtreeNode root;

    public PointQuadtree() {
        this.root = null;
    }


    

    public boolean insert(CellTower a) {
        // TO be completed by studen
        if (root == null) {
            root = new PointQuadtreeNode(a);
            return true;
        }
        else if(cellTowerAt(a.x,a.y)==true){
            return false;
        }
        else{
        root= insertRec(root, a);
        return true;
    }}

    public PointQuadtreeNode insertRec(PointQuadtreeNode root,CellTower a){
        if (root==null){
            root=new PointQuadtreeNode(a);
            return root;  
        }
        else if(root.celltower.x==a.x && root.celltower.y==a.y){

            return root;
        }
        else if(root.celltower.x<=a.x && root.celltower.y<a.y){
            root.quadrants[PointQuadtree.Quad.NE.ordinal()]=insertRec(root.quadrants[PointQuadtree.Quad.NE.ordinal()],a);
        }
        else if(root.celltower.x>a.x && root.celltower.y<=a.y){
            root.quadrants[PointQuadtree.Quad.NW.ordinal()]=insertRec(root.quadrants[PointQuadtree.Quad.NW.ordinal()],a);
        }
        else if(root.celltower.x<a.x && root.celltower.y>=a.y){
            root.quadrants[PointQuadtree.Quad.SE.ordinal()]=insertRec(root.quadrants[PointQuadtree.Quad.SE.ordinal()],a);
        }
        else if(root.celltower.x>=a.x && root.celltower.y>a.y){
            root.quadrants[PointQuadtree.Quad.SW.ordinal()]=insertRec(root.quadrants[PointQuadtree.Quad.SW.ordinal()],a);
        }
        return root;
    }
    

    

    public boolean cellTowerAt(int x, int y) {
        // TO be completed by students
        return checkat(root,x,y);
    }

    public boolean checkat(PointQuadtreeNode root,int a,int b){
        if (root==null){
            return false;  
        }
        else if(root.celltower.x==a && root.celltower.y==b){

            return true;
        }
        else if(root.celltower.x<=a && root.celltower.y<b){
            boolean res1= checkat(root.quadrants[PointQuadtree.Quad.NE.ordinal()],a,b);
            return res1;
        }
        else if(root.celltower.x>a && root.celltower.y<=b){
            boolean res2= checkat(root.quadrants[PointQuadtree.Quad.NW.ordinal()],a,b);
            return res2;
        }
        else if(root.celltower.x<a && root.celltower.y>=b){
            boolean res3= checkat(root.quadrants[PointQuadtree.Quad.SE.ordinal()],a,b);
            return res3;
        }
        else if(root.celltower.x>=a && root.celltower.y>b){
            boolean res4= checkat(root.quadrants[PointQuadtree.Quad.SW.ordinal()],a,b);
            return res4;
        }
        return false;
    }


    // public CellTower chooseCellTower(int x, int y, int r) {
    //     // TO be completed by students
        
    // }
    
    // 
    public CellTower chooseCellTower(int x, int y, int r) {
        return chooseCellTowerHelper(root, x, y, r, Double.POSITIVE_INFINITY, null);
    }
    
    private CellTower chooseCellTowerHelper(PointQuadtreeNode node, int x, int y, int r, double minDistance, CellTower cheapestTower) {
        if (node == null) {
            return cheapestTower;
        }
        double distance = node.celltower.distance(x, y);
        if (distance <= r) {
            if (node.celltower.cost < minDistance) {
                minDistance = node.celltower.cost;
                cheapestTower = node.celltower;
            }
        }
        if (node.quadrants != null) {
            for (Quad quadrant : Quad.values()) {
                if (shouldSearchQuadrant(node, x, y, r, quadrant)) {
                    cheapestTower = chooseCellTowerHelper(node.quadrants[quadrant.ordinal()], x, y, r, minDistance, cheapestTower);
                    minDistance = cheapestTower != null ? cheapestTower.cost : minDistance;
                }
            }
        }
        return cheapestTower;
    }
    
    private boolean shouldSearchQuadrant(PointQuadtreeNode node, int x, int y, int r, Quad quadrant) {
        int quadrantX = getQuadrantX(node.celltower.x, x);
        int quadrantY = getQuadrantY(node.celltower.y, y);
        switch (quadrant) {
            case NW:
                return quadrantX - node.celltower.x >= -r && quadrantY - node.celltower.y >= -r;
            case NE:
                return quadrantX - node.celltower.x <= r && quadrantY - node.celltower.y >= -r;
            case SW:
                return quadrantX - node.celltower.x >= -r && quadrantY - node.celltower.y <= r;
            case SE:
                return quadrantX - node.celltower.x <= r && quadrantY - node.celltower.y <= r;
            default:
                return false;
        }
    }
    
    private int getQuadrantX(int towerX, int x) {
        if (towerX > x) {
            return towerX - Math.abs(towerX - x) / 2;
        } else {
            return towerX + Math.abs(towerX - x) / 2;
        }
    }
    
    private int getQuadrantY(int towerY, int y) {
        if (towerY > y) {
            return towerY - Math.abs(towerY - y) / 2;
        } else {
            return towerY + Math.abs(towerY - y) / 2;
        }
    }
    }
