class Solution:
    def missingInteger(self, nums):
        # Find the longest sequential prefix
        total = nums[0]

        for i in range(1, len(nums)):
            if nums[i] == nums[i - 1] + 1:
                total += nums[i]
            else:
                break

        # Find the smallest missing integer >= total
        x = total

        while x in nums:
            x += 1

        return x