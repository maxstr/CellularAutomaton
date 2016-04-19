package mu.lambda;
import mu.lambda.*;


public class Main {

    public static void main(String[] args) {
        int automatonType = 0, m = 0, n = 0, numSteps = 0;
        double occupancy = 0.0;
        AutomatonGrid toRun;
        if (args.length != 5) {
            System.out.println("Correct calling is java mu.lambda.Main (1|2):AutomatonType int:m int:n double:occupancy int:numSteps");
            System.exit(1);
        }
        try {
            automatonType = Integer.parseInt(args[0]);
            m = Integer.parseInt(args[1]);
            n = Integer.parseInt(args[2]);
            occupancy = Float.parseFloat(args[3]);
            numSteps = Integer.parseInt(args[4]);
        }
        catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            System.out.println("Correct calling is java mu.lambda.Main (1|2):AutomatonType int:m int:n double:occupancy int:numSteps");
            System.exit(1);
        }
        if (!((automatonType == 1 || automatonType == 2) && (m > 0) && (n > 0) && (occupancy > 0 && occupancy <= 1) && (numSteps >= 0))){
            System.out.println("Correct calling is java mu.lambda.Main (1|2):AutomatonType int:m int:n double:occupancy int:numSteps");
            System.exit(1);
        }
        if (automatonType == 1) {
            toRun = new AutomatonGridType1(m, n);
        }
        else {
            toRun = new AutomatonGridType2(m, n);
        }
        toRun.gridFill(occupancy);
        System.out.println("Initial Grid: \n");
        System.out.println(toRun.toString() + "\n");
        for (int i = 0; i < numSteps; i++) {
            toRun.runIteration();
            System.out.println(toRun.toString() + "\n");
        }

    }
}
