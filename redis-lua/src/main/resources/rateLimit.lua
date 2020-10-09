local key = "rate.limit:" .. KEYS[1]    --限流KEY，  ..表示字符串连接
local limit = tonumber(ARGV[1])         --限流大小
local current = tonumber(redis.call('get', key) or "0")
if current + 1 > limit then             --如果超出限流大小
  return 0                              --被限流了
else                                    --否则，请求数+1，并设置2秒过期
  redis.call("INCRBY", key,"1")
   redis.call("expire", key,"2")
   return current + 1                   --没有被限流了，可以正常访问
end
