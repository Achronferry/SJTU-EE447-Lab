package sjtu.iiot.wi_fi_scanner_iiot;


public class ThreePointLocation {

    // d=10^((ABS(RSSI)-A)/(10*n)) A:45~49; d: 3.25~4.5
    public class NodeInfo {
        public double location[];
        public int A;
        public double n;

        NodeInfo(int A_in, double n_in, double[] loc) {
            A = A_in;
            n = n_in;
            location = loc;
        }

        public double calculate_distance(int RSSI) {
            double distance = Math.pow(10, (Math.abs(RSSI) - A) / (10 * n));
            return distance;
        }

    }

    public NodeInfo node1;
    public NodeInfo node2;
    public NodeInfo node3;

    ThreePointLocation() {
        node1 = new NodeInfo(45, 4, new double[]{0, 0});
        node2 = new NodeInfo(46, 4, new double[]{0, 2.37});
        node3 = new NodeInfo(46, 4, new double[]{1.41, 0});
    }

    // This function refers to https://blog.csdn.net/u013780605/article/details/52673223
    private double[][] intersect(double r1, double r2) {
        double x1 = node1.location[0], y1 = node1.location[1], x2 = node2.location[0], y2 = node2.location[1];

        double A = (x1 * x1 - x2 * x2 + y1 * y1 - y2 * y2 + r2 * r2 - r1 * r1) / (2 * (y1 - y2));
        double B = (x1 - x2) / (y1 - y2);

        double a = 1 + B * B;
        double b = -2 * (x1 + (A - y1) * B);
        double c = x1 * x1 + (A - y1) * (A - y1) - r1 * r1;

        double delta = b * b - 4 * a * c;

        double x_1, x_2, y_1, y_2;
        if (delta <= 0)
            delta = 0;

        x_1 = (-b + Math.sqrt(delta)) / (2 * a);
        x_2 = (-b - Math.sqrt(delta)) / (2 * a);
        y_1 = A - B * x_1;
        y_2 = A - B * x_2;

        return new double[][]{{x_1, y_1}, {x_2, y_2}};
    }

    public double cal_distance(double[] p1, double[] p2) {
        double res = (p1[0] - p2[0]) * (p1[0] - p2[0]) + (p1[1] - p2[1]) * (p1[1] - p2[1]);
        return Math.sqrt(res);
    }

    public double[] location(int RSSI1, int RSSI2, int RSSI3) {
        double r1 = node1.calculate_distance(RSSI1);
        double r2 = node2.calculate_distance(RSSI2);
        double r3 = node3.calculate_distance(RSSI3);

        double intesect_points[][] = intersect(r1, r2);

        double d1 = cal_distance(intesect_points[0], node3.location);
        double d2 = cal_distance(intesect_points[1], node3.location);

        if (Math.abs(d1 - r3) < Math.abs(d2 - r3))
            return intesect_points[0];
        else
            return intesect_points[1];
    }

}
