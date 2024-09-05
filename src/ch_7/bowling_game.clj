(ns ch-7.bowling-game)

(defn to-frames [rolls]
    (loop [remaining-rolls rolls
        frames []]
        (cond
            (empty? remaining-rolls) ; first condition
            frames

            (= 10 (first remaining-rolls)) ; second condition
            (recur (rest remaining-rolls)
                (conj frames (take 3 remaining-rolls)))

            (= 10 (reduce + (take 2 remaining-rolls))) ; third condition
            (recur (drop 2 remaining-rolls)
                (conj frames (take 3 remaining-rolls)))

            :else ; catch-all condition
            (recur (drop 2 remaining-rolls)
                (conj frames (take 2 remaining-rolls))))))

(defn add-frame [score frame]
    (+ score (reduce + frame)))

(defn score [rolls] ; Vector<int>
    (reduce add-frame 0 ; score
        (take 10 (to-frames rolls)) ; frames e.g. [[0, 1], [2, 3],,,]
        ))

;; Java implementation:
;; public class Game {
;;     private int rolls;
;;     private int score;
;;
;;     public void roll(int pins) {
;;         rolls += pins;
;;     }
;;
;;     public int score() {
;;         int score = 0;
;;         int frameIndex = 0;
;;
;;         for (int frame = 0; frame < 10; frame++) {
;;             if (isStrike(frameIndex)) {
;;                 score += 10 + strikeBonus(frameIndex);
;;                 frameIndex++;
;;             } else if (isSpare(frameIndex)) {
;;                 score += 10 + spareBonus(frameIndex);
;;                 frameIndex += 2;
;;             } else {
;;                 score += twoBallsInFrame(frameIndex);
;;                 frameIndex += 2;
;;             }
;;         }
;;         return score;
;;     }
;;
;;     private boolean isSpare(int frameIndex) {
;;         return rolls[frameIndex] + rolls[frameIndex + 1] == 10;
;;     }
;;
;;     public int score() {
;;         return score;
;;     }
;; }
