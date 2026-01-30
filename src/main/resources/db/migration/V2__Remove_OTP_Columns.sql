BEGIN;

-- Remove OTP columns from users table as OTP is now stored in Redis
ALTER TABLE IF EXISTS public.users
    DROP COLUMN IF EXISTS otp_code;

ALTER TABLE IF EXISTS public.users
    DROP COLUMN IF EXISTS otp_expiration_time;

END;
