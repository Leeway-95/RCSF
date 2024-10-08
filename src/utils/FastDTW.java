package utils;

public class FastDTW {
    // CONSTANTS
    final static int DEFAULT_SEARCH_RADIUS = 1;


    public static double getWarpDistBetween(TimeSeries tsI, TimeSeries tsJ, DistanceFunction distFn) {
        return fastDTW(tsI, tsJ, DEFAULT_SEARCH_RADIUS, distFn).getDistance();
    }


    public static double getWarpDistBetween(TimeSeries tsI, TimeSeries tsJ, int searchRadius, DistanceFunction distFn) {
        return fastDTW(tsI, tsJ, searchRadius, distFn).getDistance();
    }


    public static WarpPath getWarpPathBetween(TimeSeries tsI, TimeSeries tsJ, DistanceFunction distFn) {
        return fastDTW(tsI, tsJ, DEFAULT_SEARCH_RADIUS, distFn).getPath();
    }


    public static WarpPath getWarpPathBetween(TimeSeries tsI, TimeSeries tsJ, int searchRadius, DistanceFunction distFn) {
        return fastDTW(tsI, tsJ, searchRadius, distFn).getPath();
    }


    public static TimeWarpInfo getWarpInfoBetween(TimeSeries tsI, TimeSeries tsJ, int searchRadius, DistanceFunction distFn) {
        return fastDTW(tsI, tsJ, searchRadius, distFn);
    }


    public static TimeWarpInfo fastDTW(TimeSeries tsI, TimeSeries tsJ, int searchRadius, DistanceFunction distFn) {
        if (searchRadius < 0)
            searchRadius = 0;

        final int minTSsize = searchRadius + 2;

        if ((tsI.size() <= minTSsize) || (tsJ.size() <= minTSsize)) {
            // Perform full Dynamic Time Warping.
            return DTW.getWarpInfoBetween(tsI, tsJ, distFn);
        } else {
            final double resolutionFactor = 2.0;

            final PAA shrunkI = new PAA(tsI, (int) (tsI.size() / resolutionFactor));
            final PAA shrunkJ = new PAA(tsJ, (int) (tsJ.size() / resolutionFactor));

            // Determine the search window that constrains the area of the cost matrix that will be evaluated based on
            //    the warp path found at the previous resolution (smaller time series).
            final SearchWindow window = new ExpandedResWindow(tsI, tsJ, shrunkI, shrunkJ,
                    FastDTW.getWarpPathBetween(shrunkI, shrunkJ, searchRadius, distFn),
                    searchRadius);

            // Find the optimal warp path through this search window constraint.
            return DTW.getWarpInfoBetween(tsI, tsJ, window, distFn);
        }  // end if
    }  // end recFastDTW(...)

    public static void main(String[] args) {
//        if (args.length < 3){
//            System.out.println("Usage: java FastDTW <tsI> <tsJ> <searchRadius> [<distanceFunction>]");
//        }
//        final TimeSeries tsI = new TimeSeries(args[0], false, false, ',');
//        final TimeSeries tsJ = new TimeSeries(args[1], false, false, ',');
//
//        final DistanceFunction distFn;
//        if (args.length < 4)
//        {   // default
//            distFn = DistanceFunctionFactory.getDistFnByName("EuclideanDistance");
//        }
//        else
//        {
//            distFn = DistanceFunctionFactory.getDistFnByName(args[3]);
//        }   // end if

//
//        final TimeWarpInfo info = getWarpInfoBetween(tsI, tsJ, Integer.parseInt(args[2]), distFn);

        Double[] ts1 = {1.778976, 1.761203, 1.703084, 1.610572, 1.492088, 1.368654, 1.244761, 1.1209, 1.010762, 0.900168, 0.785766, 0.678659, 0.579652, 0.501747, 0.415444, 0.332712, 0.27374, 0.186536, 0.096384, 0.008932, -0.074871, -0.154732, -0.230341, -0.307648, -0.398139, -0.486768, -0.522666, -0.593812, -0.667186, -0.725148, -0.778903, -0.841215, -0.898453, -0.950257, -0.99949, -1.036129, -1.075394, -1.115995, -1.156217, -1.169245, -1.174798, -1.193852, -1.184477, -1.210008, -1.206939, -1.196454, -1.178665, -1.153747, -1.121909, -1.103466, -1.054466, -1.021469, -0.969603, -0.907503, -0.858248, -0.816144, -0.763896, -0.714655, -0.64024, -0.581885, -0.504507, -0.42492, -0.352908, -0.289087, -0.208767, -0.135969, -0.048568, 0.031993, 0.116337, 0.199361, 0.279839, 0.358126, 0.437559, 0.481654, 0.577155, 0.672861, 0.774367, 0.888604, 1.011793, 1.114325, 1.237777, 1.363941, 1.48549, 1.604407, 1.707524, 1.780825, 1.825778, 1.80093, 1.800304, 1.796303, 1.755737, 1.661379, 1.544747, 1.423028, 1.304039, 1.182978, 1.062608, 0.948054, 0.835447, 0.73153, 0.632545, 0.537837, 0.458698, 0.368604, 0.276708, 0.186014, 0.100695, 0.007481, -0.085104, -0.164473, -0.250678, -0.33357, -0.423105, -0.511076, -0.582589, -0.668592, -0.732385, -0.79883, -0.882998, -0.93372, -0.976332, -1.060667, -1.093758, -1.146509, -1.183283, -1.232713, -1.255488, -1.311567, -1.32346, -1.319262, -1.353275, -1.315935, -1.334201, -1.313147, -1.283926, -1.278637, -1.245231, -1.204913, -1.177471, -1.106129, -1.075399, -1.029827, -0.960874, -0.917801, -0.839785, -0.78567, -0.714393, -0.645017, -0.566571, -0.497889, -0.404519, -0.327619, -0.243941, -0.160766, -0.075812, 0.014266, 0.107014, 0.193531, 0.276297, 0.361518, 0.451692, 0.541502, 0.634348, 0.722392, 0.822148, 0.928921, 1.044818, 1.165685, 1.285657, 1.408878, 1.507983, 1.623643, 1.713606, 1.766389, 1.783633, 1.758625, 2.0, 5.0};
        Double[] ts2 = {1.720747, 1.731913, 1.725778, 1.692565, 1.645378, 1.552768, 1.460055, 1.36681, 1.271866, 1.175457, 1.079547, 0.980138, 0.883176, 0.78445, 0.697811, 0.601092, 0.507217, 0.413572, 0.320328, 0.227959, 0.136561, 0.046232, -0.042755, -0.130739, -0.218026, -0.293201, -0.376234, -0.462093, -0.541116, -0.623003, -0.696979, -0.771761, -0.838139, -0.90459, -0.964418, -1.026691, -1.078342, -1.130986, -1.177762, -1.215749, -1.242134, -1.267366, -1.282329, -1.294485, -1.302372, -1.304325, -1.297712, -1.282612, -1.264421, -1.246527, -1.216585, -1.192466, -1.148902, -1.114612, -1.058513, -1.011859, -0.955521, -0.896292, -0.830323, -0.755764, -0.683732, -0.607032, -0.527413, -0.445659, -0.36326, -0.278901, -0.201695, -0.114069, -0.034114, 0.053739, 0.13544, 0.228022, 0.313061, 0.405827, 0.501823, 0.598429, 0.695589, 0.792975, 0.890472, 0.990143, 1.079349, 1.177147, 1.274152, 1.37141, 1.46661, 1.562001, 1.658379, 1.711463, 1.74022, 1.740623, 1.725152, 1.676253, 1.596706, 1.506845, 1.422582, 1.328998, 1.23358, 1.137302, 1.040146, 0.94285, 0.846435, 0.750614, 0.65476, 0.559349, 0.467342, 0.378102, 0.291862, 0.20097, 0.111223, 0.022717, -0.065211, -0.152301, -0.237862, -0.321706, -0.405166, -0.479946, -0.55802, -0.626576, -0.703654, -0.768725, -0.838305, -0.893827, -0.95918, -1.006889, -1.062936, -1.113895, -1.158396, -1.193628, -1.221599, -1.243272, -1.258793, -1.275664, -1.284207, -1.284298, -1.275965, -1.26874, -1.253205, -1.229631, -1.206366, -1.180086, -1.14478, -1.093396, -1.049562, -0.997497, -0.943024, -0.87296, -0.812943, -0.742062, -0.668494, -0.60153, -0.524541, -0.452055, -0.369709, -0.294206, -0.204446, -0.120601, -0.031484, 0.056867, 0.146622, 0.230727, 0.323009, 0.416011, 0.511698, 0.60567, 0.702465, 0.790345, 0.88768, 0.986565, 1.080519, 1.173328, 1.261211, 1.359161, 1.454301, 1.55109, 1.633517, 1.692053, 25.0, 21.0};
        TimeSeries tsI = new TimeSeries(ts1);
        TimeSeries tsJ = new TimeSeries(ts2);
        DistanceFunction distFn_Euc = DistanceFunctionFactory.EUCLIDEAN_DIST_FN;
        DistanceFunction distFn_Man = DistanceFunctionFactory.MANHATTAN_DIST_FN;
        DistanceFunction distFn_Bin = DistanceFunctionFactory.BINARY_DIST_FN;

        int searchRadius = 1;
        double distance_Euc = fastDTW(tsI, tsJ, searchRadius, distFn_Euc).getDistance();
        double distance_Man = fastDTW(tsI, tsJ, searchRadius, distFn_Man).getDistance();
        double distance_Bin = fastDTW(tsI, tsJ, searchRadius, distFn_Bin).getDistance();
        System.out.println(distance_Euc);
        System.out.println(distance_Man);
        System.out.println(distance_Bin);
    }
}
