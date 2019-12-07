public class LeeCode {
    public static void main(String[] args) {
        int []nums = {2,-1,2,3,1,-4,1,-12};
        System.out.println(Findmaxnumber(nums));
    }

    private static int Findmaxnumber(int[] nums) {
        int end = 0;
        int start = 0;
        int sum = 0;
        int max= 0;
     while(end<nums.length && start<nums.length){
         if(sum>=0){
             sum+=nums[start++];
         }else{
             sum-=nums[end++];
         }
         max = Math.max(sum,max);
     }
     return max;
    }
}
