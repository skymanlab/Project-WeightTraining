package com.skyman.weighttrainingpro.management.event.program.util;

import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.data.Event;
import com.skyman.weighttrainingpro.management.event.program.data.GroupingEventData;
import com.skyman.weighttrainingpro.management.project.data.type.ProgramType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RandomEventSelectionUtil {

    // constant
    private static final String CLASS_NAME = "[EPu]_RandomEventSelectionUtil";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private ArrayList<Event> integratedEventArrayList;   // type 순서대로 통합한 eventArrayList
    private GroupingEventData groupingEventData;

    // instance variable
    private int allGroupSelectedCount;          // 전체 그룹에서 선택되어진 개수
    private int aGroupSelectedCount;            // 1. a group selectedCount
    private int bGroupSelectedCount;            // 2. b group selectedCount
    private int cGroupSelectedCount;            // 3. c group selectedCount
    private int dGroupSelectedCount;            // 4. d group selectedCount
    private int eGroupSelectedCount;            // 5. e group selectedCount

    // instance variable
    private ProgramType programType;

    // instance variable :
    private ArrayList<Event> randomSelectedEventArrayList = null;
    private ArrayList<Event> noSelectedEventArrayList = null;

    // constructor
    public RandomEventSelectionUtil(ProgramType programType, int aGroupSelectedCount, int bGroupSelectedCount, int cGroupSelectedCount, int dGroupSelectedCount, int eGroupSelectedCount) {
        this.randomSelectedEventArrayList = new ArrayList<>();
        this.noSelectedEventArrayList = new ArrayList<>();

        this.programType = programType;
        this.aGroupSelectedCount = aGroupSelectedCount;
        this.bGroupSelectedCount = bGroupSelectedCount;
        this.cGroupSelectedCount = cGroupSelectedCount;
        this.dGroupSelectedCount = dGroupSelectedCount;
        this.eGroupSelectedCount = eGroupSelectedCount;
    }

    // constructor
    public RandomEventSelectionUtil(ProgramType programType, int allGroupSelectedCount) {
        this.randomSelectedEventArrayList = new ArrayList<>();
        this.noSelectedEventArrayList = new ArrayList<>();

        this.programType = programType;
        this.allGroupSelectedCount = allGroupSelectedCount;
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= integratedEventArrayList =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public ArrayList<Event> getIntegratedEventArrayList() {

        final String METHOD_NAME = "[getIntegratedEventArrayList] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** integratedEventArrayList 확인 ****");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, integratedEventArrayList);
        return integratedEventArrayList;

    } // End of method [getIntegratedEventArrayList]


    public void setIntegratedEventArrayList() {

        // [iv/C]ArrayList<Event> : integratedEventArrayList 객체 생성
        this.integratedEventArrayList = new ArrayList<>();


        // [iv/C]ArrayList<Event> : aGroupEventArrayList
        this.integratedEventArrayList.addAll(this.groupingEventData.getAGroupEventArrayList());
        this.integratedEventArrayList.addAll(this.groupingEventData.getBGroupEventArrayList());
        this.integratedEventArrayList.addAll(this.groupingEventData.getCGroupEventArrayList());
        this.integratedEventArrayList.addAll(this.groupingEventData.getDGroupEventArrayList());
        this.integratedEventArrayList.addAll(this.groupingEventData.getEGroupEventArrayList());

    } // End of method [setIntegratedEventArrayList]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= groupingEventData =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public GroupingEventData getGroupingEventData() {
        return groupingEventData;
    }

    public void setGroupingEventData(GroupingEventData groupingEventData) {
        this.groupingEventData = groupingEventData;
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= RandomSelectedGroupEventList =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public ArrayList<Event> getRandomSelectedEventArrayList() {

        final String METHOD_NAME = "[getRandomSelectedEventArrayList] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** randomSelectedEventArrayList 확인 ****");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.randomSelectedEventArrayList);

        return randomSelectedEventArrayList;

    } // End of method [getRandomSelectedEventArrayList]


    /**
     * [method] eventArrayList 에서 randomIndex 의 index 값에 해당하는 Event 객체를 randomSelectedEventArrayList 에 추가한다.
     */
    public void addRandomSelectedEventArrayList(ArrayList<Event> eventArrayList) {

        this.randomSelectedEventArrayList.addAll(eventArrayList);

    } // End of method [addRandomSelectedEventArrayList]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= noSelectedEventArrayList =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public ArrayList<Event> getNoSelectedEventArrayList() {

        final String METHOD_NAME = "[getNoSelectedEventArrayList] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** noSelectedEventArrayList 확인 ****");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.noSelectedEventArrayList);

        return noSelectedEventArrayList;
    } // End of method [getNoSelectedEventArrayList]


    public void addNoSelectedEventArrayList(ArrayList<Event> eventArrayList) {

        this.noSelectedEventArrayList.addAll(eventArrayList);


    } // End of method [addNoSelectedEventArrayList]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= random selection util =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method]
     */
    public void selectRandomEvent() {

        final String METHOD_NAME = "[selectRandomEvent] ";

        // [check 1] : aGroupEventArrayList, bGroupEventArrayList, cGroupEventArrayList, dGroupEventArrayList, eGroupEventArrayList 의 객체가 생성되었다.
        if ((this.groupingEventData.getAGroupEventArrayList() != null)
                && (this.groupingEventData.getBGroupEventArrayList() != null)
                && (this.groupingEventData.getCGroupEventArrayList() != null)
                && (this.groupingEventData.getDGroupEventArrayList() != null)
                && (this.groupingEventData.getEGroupEventArrayList() != null)) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 모든 group 별 eventArrayList 가 입력되었습니다. <=");


            // [check 2] : program type 이 뭐냐?

            switch (this.programType) {

                case EACH_RANDOM:
                    // 각각의 그룹에서 무작위 선택
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "==> a group 랜덤 선택  <++++++++");
                    divideEventArrayList(this.groupingEventData.getAGroupEventArrayList(), this.aGroupSelectedCount);
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "==> b group 랜덤 선택  <++++++++");
                    divideEventArrayList(this.groupingEventData.getBGroupEventArrayList(), this.bGroupSelectedCount);
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "==> c group 랜덤 선택  <++++++++");
                    divideEventArrayList(this.groupingEventData.getCGroupEventArrayList(), this.cGroupSelectedCount);
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "==> d group 랜덤 선택  <++++++++");
                    divideEventArrayList(this.groupingEventData.getDGroupEventArrayList(), this.dGroupSelectedCount);
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "==> e group 랜덤 선택  <++++++++");
                    divideEventArrayList(this.groupingEventData.getEGroupEventArrayList(), this.eGroupSelectedCount);

                    // 결과
//                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//                    getRandomSelectedEventArrayList();
//                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//                    getNoSelectedEventArrayList();
                    break;

                case ALL_RANDOM:

                    // [iv/C]ArrayList<Event> : 각각의 그룹을 순서대로 통합하기
                    setIntegratedEventArrayList();

                    // [method] : integratedEventArrayList 에서 allGroupSelectedCount(선택한 개수) 만큼 임의로 선택된 값들을 randomSelectedEventArrayList 에 추가하기
                    divideEventArrayList(this.integratedEventArrayList, this.allGroupSelectedCount);

                    // 결과
//                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//                    getRandomSelectedEventArrayList();
//                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//                    getNoSelectedEventArrayList();
                    break;

                default:
                    break;

            } // [cycle 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 일단 Group 별 eventArrayList 를 입력해주세요! <=");
        } // [check 1]

    } // End of method [selectRandomEvent]


    /**
     * [method] selectedCount 만큼 랜덤으로 index 를 골라낸다. 이것으로 선택된 것과 선택되지 않은 것으로 나눈다.
     */
    private void divideEventArrayList(ArrayList<Event> eventArrayList, int selectedCount) {

        // [lv/C]IndexList : 랜덤으로 선택된 index 리스트와 선택되지 않은 index 리스트를 구분한다.
        IndexList indexList = generateRandomOfIndex(selectedCount, eventArrayList.size());

        // [method] : 랜덤으로 선택된 index 들만 randomSelectedEventArrayList 에 저장
        addRandomSelectedEventArrayList(generateEventArrayList(indexList.getSelectedIndex(), eventArrayList));

        // [method] : 선택되지 않은 나머지 index 들만 noSelectedEventArrayList 에 저장
        addNoSelectedEventArrayList(generateEventArrayList(indexList.getNoSelectedIndex(), eventArrayList));

    } // End of method [divideEventArrayList]


    /**
     * [method] [random] 해당 eventArrayList 에서 랜덤으로 선택된 index 에 해당하는 Event 객체만 ArrayList 에 추가하여 반환한다.
     *
     * @param indexList      index 가 저장된 배열
     * @param eventArrayList 그룹별 또는 통합된 eventArrayList
     * @return
     */
    private ArrayList<Event> generateEventArrayList(int[] indexList, ArrayList<Event> eventArrayList) {

        final String METHOD_NAME = "[generateEventArrayList] ";

        // [lv/C]ArrayList<Event> : eventArrayList 중에 randomIndex 의 index 에 해당하는 Event 객체만 ArrayList 에 추가한다.
        ArrayList<Event> randomEventArrayList = new ArrayList<>();

        // [check 1] : 해당 eventArrayList 의 데이터가 있을때만
        if (eventArrayList.size() != 0) {

            // [check 2] : 해당 indexList 인 int 배열이 생성되었을 때만.
            if (indexList != null) {

                for (int index = 0; index < indexList.length; index++) {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, index + " 번째 = " + indexList[index]);
                }


                // [check 1] : randomIndex 의 length 와 eventArrayList 의 size 가 같다.
                if (indexList.length == eventArrayList.size()) {

                    // [ic/C]ArrayList<Event> : eventArrayList 의 모든 데이터를 randomSelectedEventArrayList 에 추가한다.
                    randomEventArrayList.addAll(eventArrayList);

                } else {

                    // [check 1] : randomIndex 의 length 만큼
                    for (int index = 0; index < indexList.length; index++) {

                        // [iv/C]ArrayList<Event> : event 를 추가한다.
                        randomEventArrayList.add(eventArrayList.get(indexList[index]));

                    } // [check 1]

                } // [check 1]
            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : indexList 는 null 이다. <=");
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : eventArrayList 의 size 가 0 이다.<=");
        } // [check 1]

        return randomEventArrayList;

    } // End of method [generateRandomEventArrayList]


    /**
     * [method] [random] 총 개수에서 선택된 개수 만큼
     *
     * @param selectedCount 총 개수 중에서 랜덤으로 구하려는 수의 개수
     * @param endRange      랜덤으로 구하려는 수의 끝 범위(총 개수)
     */
    private IndexList generateRandomOfIndex(int selectedCount, int endRange) {

        final String METHOD_NAME = "[generateRandomIndex] ";

        // [lv/C]Random : 랜덤 함수 생성
        Random random = new Random();

        // [lv/C]IndexList :
        IndexList result = null;


        // [check 1] : selectedCount 와 endRange 가 같다.
        if (selectedCount == endRange) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : selectedCount 와 endRange 가 같습니다. 기존 index 값을 차례대로 입력합니다. <=");

            // [lv/i]randomIndex : 랜덤으로 선택된 값 / selectedCount 크기의 배열 생성
            int[] randomSelectedIndexList = new int[selectedCount];

            // [cycle 1] : 선택한 개수 만큼
            for (int index = 0; index < selectedCount; index++) {

                // [lv/i]randomIndex : 0 부터 차레대로 입력
                randomSelectedIndexList[index] = index;

            } // [cycle 1]

            result = new IndexList(endRange, 0);
            result.setSelectedIndex(randomSelectedIndexList);

        } else {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : selectedCount 와 endRange 가 달라요! 랜덤으로 선택! <=");

            // =-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            // [lv/i]randomIndex : 랜덤으로 선택된 값 / selectedCount 크기의 배열 생성
            int[] randomSelectedIndexList = new int[selectedCount];

            // [cycle 2] : 선택한 개수 만큼
            for (int index = 0; index < selectedCount; index++) {

                // [lv/i]randomIndex : 0 부터 endRange 범위에서 무작위로 값 생성
                randomSelectedIndexList[index] = random.nextInt(endRange);

                // [cycle 3] : index 만큼
                for (int comparedIndex = 0; comparedIndex < index; comparedIndex++) {

                    // [check 2] : 새롭게 생성된 random 수가 그 전 배열의 값과 같은 것이 있을 경우
                    if (randomSelectedIndexList[index] == randomSelectedIndexList[comparedIndex]) {

                        // [lv/i]index : 같은 값이 있으므로 해당 index 번째에 다시 값을 생성하여 넣기 위해 -1 하기 / because cycle 1 에서 index++ 해주므로 원래 index 값을 만들기 위해서 index-- 해줘야 합니다.
                        index--;

                    } // [check 2]
                } // [cycle 3]
            } // [cycle 2]

            // [method] : randomIndex 의 무작위로 선택된 값이 정렬이 되지 않았으므로 오름차순으로 정렬한다.
            Arrays.sort(randomSelectedIndexList);

            // =-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            // [lv/i]noSelectedIndex :
            int[] noSelectedIndexList = new int[endRange - selectedCount];

            int indexN = 0;

            // [cycle 4] : index 의 총 개수 만큼
            for (int index = 0; index < endRange; index++) {

                // [lv/i]checker : 같은 것이 몇 개 있는 지 검사
                int checker = 0;

                // [cycle 5] : 랜덤으로 선택된 index 가 있는
                for (int indexR = 0; indexR < selectedCount; indexR++) {

                    // [check 3] : 해당 index 가 랜덤으로 선택된 index 가 다를 때만
                    if (index != randomSelectedIndexList[indexR]) {

                        // [lv/i]checker : 다른 것이 있을 때 +1 증가하기
                        checker++;

                    } // [check 3]

                } // [cycle 5]

                // [check 4] : checker 와 randomSelectedIndex.length 가 같다. 즉, randomSelectedIndex 의 index 와 모두 같지 않으므로 선택되지 않은 index 가 되는 것이다.
                if (checker == selectedCount) {

                    // [lv/i]noSelectedIndexList : 해당 index 를 noSelectedIndexList 에 추가한다.
                    noSelectedIndexList[indexN++] = index;

                } // [check 4]

            } // [cycle 4]

            // [lv/C]IndexList : randomSelectedIndexList 와 noSelectedIndexList 를
            result = new IndexList(selectedCount, endRange - selectedCount);
            result.setSelectedIndex(randomSelectedIndexList);
            result.setNoSelectedIndex(noSelectedIndexList);

        } // [check 1]

        return result;
    } // End of method [generateRandomIndex]


    /**
     * [innerClass] 선택 된 index 와 선택되지 않은 index 를 표현
     */
    class IndexList {

        // instance variable
        int[] selectedIndex;
        int[] noSelectedIndex;

        // constructor
        public IndexList(int selectedIndexSize, int noSelectedIndexSize) {

            if (selectedIndexSize != 0) {
                this.selectedIndex = new int[selectedIndexSize];
            } else {
                this.selectedIndex = null;
            }

            if (noSelectedIndexSize != 0) {
                this.noSelectedIndex = new int[noSelectedIndexSize];
            } else {
                this.noSelectedIndex = null;
            }
        }

        public int[] getSelectedIndex() {
            return selectedIndex;
        }

        public void setSelectedIndex(int[] selectedIndex) {
            this.selectedIndex = selectedIndex;
        }

        public int[] getNoSelectedIndex() {
            return noSelectedIndex;
        }

        public void setNoSelectedIndex(int[] noSelectedIndex) {
            this.noSelectedIndex = noSelectedIndex;
        }
    }

}
