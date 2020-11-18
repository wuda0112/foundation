/**
 * 维护权限体系模型.
 * Subject对Target有执行Action的权限.
 *
 * <h1>permission-vs-privilege</h1>
 * <p>In computer security, they are used interchangeably.
 * <p>
 * In general usage, neither indicates a "right", as a "right" is normally understood to refer to something that is inherent or earned and may not be taken away,
 * where "permission" and "privilege" are granted and may be taken away. For example, you have a legal RIGHT to freedom of speech,
 * but it would be a PRIVILEGE to be allowed to give a speech to a joint session of Congress. That is, every citizen has a right to speak in general,
 * but to speak to a particular group or at a particular time and place would be a privilege.
 * It is common to hear someone say that being allowed to do this or that "is a privilege and not a right", meaning that they don't have to let you do it.
 * <p>
 * As to the difference between permission and privilege: "Privilege" means a special or unusual permission or status. Like many words, there is no hard and fast distinction,
 * but you should use "privilege" only when you want to convey the idea that this is something special. For example, if you said, "The student was given permission to go to the library",
 * the hearer would understand that while not all students necessarily got such permission, it was a fairly routine matter. But if you said, "The student was given the privilege of going to the library",
 * this would imply that very few students were allowed to do this, or that it was considered a wonderful thing to be able to do.
 * <p>
 * As a technical note, we always say "the privilege" or "a privilege", but simply "permission". Like, "I was given permission", or "I was given the privilege".</p>
 */
package com.wuda.foundation.security;