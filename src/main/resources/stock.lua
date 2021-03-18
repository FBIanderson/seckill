--
-- Created by IntelliJ IDEA.
-- User: Anderson
-- Date: 2021/3/18
-- Time: 11:06
-- To change this template use File | Settings | File Templates.
--
if(redis.call("exist",KEYS[1]) == 1) then
    local stock = tonumber(redis.call("get",KEYS[1]));
    if(stock>0) then
        redis.call("incryby",KEYS[1],-1);
        return stock;
    end;
        return 0;
end;
