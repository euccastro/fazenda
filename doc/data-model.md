# Data Model

We aim towards flexibility and openness of "types**. Attributes work more like
mixins than type specifiers.

## Entity types

### user

A user of the system.

#### Attributes

**:user/name**
: String with max 64 characters.  Globally unique identifier for a user.

In the future we will have password hash.

To begin with, we will have a single user and no authentication. We still want
the data model to support multiple users because it would be no fun to migrate
to that later.

**:user/in**
: Each user has a scratch page to jot down unorganized notes to come back to later for better organization.
:
: This is an entity with a `:body`.

## Generic attributes

**:body**
: Searchable text with unlimited length.  We may treat it as plain text or Markdown depending on the context.

### :tags

Keywords that may be attached to any entity.
