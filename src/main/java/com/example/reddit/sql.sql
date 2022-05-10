


SELECT u.id, u.created_at AS userCreatedAt, u.username, u.email, u.post_amounts, p.id AS postId, p.created_at AS postCreatedAt, p.updated_at AS postUpdatedAt, p.title, p.content, p.view_count, p.vote_points, p.like_points, p.confused_points, p.laugh_points, p.comment_amounts FROM post p LEFT JOIN user u ON p.user_id = u.id;



SELECT u.id, u.created_at AS userCreatedAt, u.username, u.email, u.post_amounts, p.id AS postId, p.created_at AS postCreatedAt, p.updated_at AS postUpdatedAt, p.title, p.content, p.view_count, p.vote_points, p.like_points, p.confused_points, p.laugh_points, p.comment_amounts, i.created_at AS interactionCreatedAt, i.updated_at AS interactionUpdatedAt, i.vote_status, i.like_status, i.laugh_status, i.confused_status, i.have_read, i.have_checked FROM post p LEFT JOIN user u ON p.user_id = u.id LEFT JOIN interactions i on i.post_id = p.id and p.id = 1 and i.user_id = 1;
