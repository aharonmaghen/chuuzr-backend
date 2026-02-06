BEGIN;

-- Add the column as nullable first
ALTER TABLE public.rooms 
ADD COLUMN option_type_id bigint;

-- Set existing data to your default (ID = 1)
UPDATE public.rooms 
SET option_type_id = 1 
WHERE option_type_id IS NULL;

-- Now enforce NOT NULL and add the Foreign Key
ALTER TABLE public.rooms 
ALTER COLUMN option_type_id SET NOT NULL;

ALTER TABLE public.rooms
ADD CONSTRAINT option_type_id_fkey FOREIGN KEY (option_type_id)
REFERENCES public.option_types (id);

END;