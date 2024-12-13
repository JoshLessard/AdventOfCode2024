package com.adventofcode2024.dec13;

import java.util.OptionalLong;

class ClawMachine {

    private final Button buttonA;
    private final Button buttonB;
    private final PrizePosition prizePosition;

    ClawMachine( Button buttonA, Button buttonB, PrizePosition prizePosition ) {
        this.buttonA = buttonA;
        this.buttonB = buttonB;
        this.prizePosition = prizePosition;
    }

    OptionalLong fewestTokensToWinPrize() {
        EquationSolution solution = solve(
            new Equation( buttonA.deltaX(), buttonB.deltaX(), prizePosition.x() ),
            new Equation( buttonA.deltaY(), buttonB.deltaY(), prizePosition.y() )
        );
        return isSolution( solution )
            ? OptionalLong.of( buttonA.numberOfTokensPerPush() * solution.aButtonPresses() + buttonB.numberOfTokensPerPush() * solution.bButtonPresses() )
            : OptionalLong.empty();
    }

    private EquationSolution solve( Equation equation1, Equation equation2 ) {
        Equation equationWithZeroAValue = equation1.subtract( equation2 );
        long bValue = equationWithZeroAValue.answer() / equationWithZeroAValue.bCoefficient();
        long aValue = equation1.solveForA( bValue );
        return new EquationSolution( aValue, bValue );
    }

    private boolean isSolution( EquationSolution solution ) {
        PrizePosition solutionPoint = new PrizePosition(
            solution.aButtonPresses() * buttonA.deltaX() + solution.bButtonPresses() * buttonB.deltaX(),
            solution.aButtonPresses() * buttonA.deltaY() + solution.bButtonPresses() * buttonB.deltaY()
        );
        return solutionPoint.equals( prizePosition );
    }

    ClawMachine adjustPrizePositionBy( long delta ) {
        return new ClawMachine(
            buttonA,
            buttonB,
            new PrizePosition( prizePosition.x() + delta, prizePosition.y() + delta )
        );
    }
}
